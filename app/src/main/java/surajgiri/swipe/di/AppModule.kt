package surajgiri.swipe.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import surajgiri.swipe.addproduct.repository.AddProductRepository
import surajgiri.swipe.addproduct.viewmodel.AddProductViewModel
import surajgiri.swipe.listproduct.repository.ListProductRepository
import surajgiri.swipe.listproduct.viewmodel.ListProductViewModel

val appModule = module {
    viewModel { ListProductViewModel(get()) }
    viewModel { AddProductViewModel(get()) }
    single { ListProductRepository(get()) }
    single { AddProductRepository(get()) }
}
