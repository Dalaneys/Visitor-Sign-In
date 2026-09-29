package stu.gpt.signing.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import android.util.Log

/**
 * Runs at specified time daily (scheduled from Repository.initialize).
 * - Signs out all currently signed-in Visitors and Stage members
 * - Auto export if enabled (performed AFTER sign-out to include everyone)
 * - Applies history retention and persists
 */
class DailyMaintenanceWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("DailyMaintenanceWorker", "Starting daily maintenance job…")

            // 1. Check for database changes and perform "Last Performance" date check.
            Repository.refreshAllFromPersistedUrisIfChanged(conditionalCast = true)

            // 2. Sign-out everyone currently signed-in (visitors + Regular List)
            Repository.signOutAllNow()
            Repository.regularListSignOutAllNow()

            // Auto export full history to default folder AFTER sign-out
            // (Handled regularly by hourly record saves)

            // Apply retention and persist
            Repository.applyRetentionNow()
            
            // 3. Save daily records to "Records" subfolder and prune old ones
            Repository.saveDailyRecordFile()
            Repository.pruneDailyRecordsOlderThan30Days()

            // 4. Reset Warehouse active state to OFF
            Repository.resetWarehouseState()
            
            // Prune old admin logs (30 days rolling retention)
            Repository.pruneAdminLogsOlderThan30Days()

            Log.d("DailyMaintenanceWorker", "Daily maintenance job completed successfully.")
            Result.success()
        } catch (t: Throwable) {
            Log.e("DailyMaintenanceWorker", "Daily maintenance job failed: ${t.message}", t)
            Result.retry()
        }
    }
}
