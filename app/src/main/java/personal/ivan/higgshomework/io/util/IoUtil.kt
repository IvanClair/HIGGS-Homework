package personal.ivan.higgshomework.io.util

import androidx.lifecycle.liveData
import personal.ivan.higgshomework.io.model.IoRqStatusModel

/**
 * @param S source from network or database
 * @param R result of output
 */
abstract class IoUtil<S, R> {

    // Result LiveData
    private val resultLiveData =
        liveData<IoRqStatusModel<R>> {

            // start with loading status
            emit(IoRqStatusModel.loading())

            // TODO Retrofit throws exception if network is unavailable,
            //     maybe they will update in future, keep an eye on new release
            try {

                // load from database
                val dataFromDb = loadFromDb()
                if (dataFromDb != null) {
                    convertFromSource(source = dataFromDb)
                        ?.also { emit(IoRqStatusModel.success(data = it)) }
                }

                // load from network
                val dataFromNetwork = loadFromNetwork()

                // convert API response to actual needed model
                val convertedData = convertFromSource(source = dataFromNetwork)
                when {
                    // load from network succeed
                    convertedData != null -> {
                        dataFromNetwork?.also { saveToDb(data = it) }
                        emit(IoRqStatusModel.success(data = convertedData))
                    }

                    // load from network failed, and did not save in database before
                    dataFromDb == null -> emit(IoRqStatusModel.fail())
                }
            } catch (e: Exception) {
                // set fail when exception happened
                emit(IoRqStatusModel.fail())
            }
        }

    // region Public Function

    fun getLiveData() = resultLiveData

    // endregion

    // region Override Functions

    protected abstract suspend fun loadFromDb(): S?

    protected abstract suspend fun loadFromNetwork(): S?

    protected abstract suspend fun convertFromSource(source: S?): R?

    protected abstract suspend fun saveToDb(data: S)

    // end region
}