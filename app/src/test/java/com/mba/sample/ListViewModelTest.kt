package com.mba.sample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mba.sample.model.Country
import com.mba.sample.model.CountryService
import com.mba.sample.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUpRxSchedulers() {
        val immideate = object : Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }

            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immideate }
        RxJavaPlugins.setComputationSchedulerHandler { scheduler -> immideate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immideate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immideate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immideate }
    }


    @Mock
    lateinit var countryService: CountryService

    @InjectMocks
    var listViewModel = ListViewModel()

    private var testSingle: Single<List<Country>>? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getCountriesSuccess() {
        val country = Country("name", "capital", "url")
        testSingle = Single.just(arrayListOf(country))

        `when`(countryService.getCountries()).thenReturn(testSingle)

        listViewModel.refresh()

        Assert.assertEquals(1, listViewModel.countries.value?.size)
        Assert.assertEquals(false, listViewModel.countryLoadError.value )
        Assert.assertEquals(false, listViewModel.loading.value )
    }


}