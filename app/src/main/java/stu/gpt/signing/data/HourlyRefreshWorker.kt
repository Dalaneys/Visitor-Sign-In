package stu.gpt.signing.data

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

/**
 * Hourly refresh worker that:
 * - Refreshes all databases from the last-picked document URIs (Names, Roles, Locations, Stage)
 */
class HourlyRefreshWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("HourlyRefreshWorker", "Starting hourly refresh job…")

            // Refresh all databases from persisted URIs if changed
            Repository.refreshAllFromPersistedUrisIfChanged(false)

            // Save records/logs regularly and check meeting timeout
            Repository.saveDailyRecordFile()
            Repository.checkAndApplyMeetingTimeout()

            Log.d("HourlyRefreshWorker", "Hourly refresh job completed successfully.")
            Result.success()
        } catch (t: Throwable) {
            Log.e("HourlyRefreshWorker", "Hourly refresh job failed: ${t.message}", t)
            Result.retry()
        }
    }
}
