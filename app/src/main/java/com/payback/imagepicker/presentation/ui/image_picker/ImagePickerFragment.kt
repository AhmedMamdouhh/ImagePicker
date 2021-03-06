package com.payback.imagepicker.presentation.ui.image_picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.payback.imagepicker.databinding.FragmentImagePickerBinding
import com.payback.imagepicker.domain.model.image.Image
import com.payback.imagepicker.manager.utilities.*
import com.payback.imagepicker.presentation.utils.Constants
import com.payback.imagepicker.presentation.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


@AndroidEntryPoint
class ImagePickerFragment : Fragment() {

    private lateinit var imagePickerBinding: FragmentImagePickerBinding
    val imagePickerViewModel: ImagePickerViewModel by viewModels()

    private lateinit var imagePickerAdapter: ImagePickerAdapter
    private lateinit var imageListOffline: ArrayList<Image>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        imagePickerBinding = FragmentImagePickerBinding.inflate(inflater, container, false)
        imagePickerBinding.imagePickerListener = imagePickerViewModel

        observeImageList()
        observeSearchText()
        observeImageClicked()
        observeSearchResultNotFound()
        observeOfflineMode()
        observeOnlineMode()



        return imagePickerBinding.root
    }


    private fun observeSearchResultNotFound() {
        imagePickerViewModel.observeNotFound.observe(viewLifecycleOwner,
            EventObserver {
                imagePickerBinding.llImagePickerNotFoundContainer.visibility = VISIBLE
            })
    }

    private fun observeOfflineMode() {
        imagePickerViewModel.observeOfflineMode.observe(viewLifecycleOwner,
           EventObserver {
                imagePickerBinding.apply {
                    connectionSwitcher(
                        ivCurrencyPickerTopCloud,
                        ivCurrencyPickerBottomCloud,
                        cvImagePickerOfflineDialog,
                        searchView,
                        false
                    )
                }
            })
    }

    private fun observeOnlineMode() {
        imagePickerViewModel.observeOnlineMode.observe(viewLifecycleOwner,
            EventObserver {
                imagePickerBinding.apply {
                    connectionSwitcher(
                        ivCurrencyPickerTopCloud,
                        ivCurrencyPickerBottomCloud,
                        cvImagePickerOfflineDialog,
                        searchView,
                        true
                    )
                }
            })
    }


    private fun observeImageClicked() {
        imagePickerViewModel.observeImageClicked.observe(
            viewLifecycleOwner,
            EventObserver { imageObject ->
                val action =
                    ImagePickerFragmentDirections.actionImagePickerToImageDetailsDialog(
                        imageObject
                    )
                showConfirmDialog(action)
            })
    }


    private fun observeImageList() {
        imagePickerViewModel.observeImageListData.observe(
            viewLifecycleOwner,
            EventObserver { imageList ->
                imageListOffline = imageList
                initializeImageList(imageList)
            })
    }

    private fun observeSearchText() {
        imagePickerBinding.apply {
            searchView.getQueryTextChangeObservable()
                .debounce(Constants.DELAY_SMALL, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    if (result.isNotEmpty())
                        imagePickerBinding.llImagePickerNotFoundContainer.visibility = GONE
                    imagePickerViewModel.filterSearchKeyWord(result)
                }
        }

    }

    private fun initializeImageList(imageList: ArrayList<Image>) {
        imagePickerAdapter = ImagePickerAdapter(imageList, imagePickerViewModel)
        imagePickerBinding.apply {
            rvImageList.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = imagePickerAdapter
                recyclerAnimationExtension(this)
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(Constants.GSON_KEY, convertFromImageListToString(imageListOffline))
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        if (savedInstanceState?.containsKey(Constants.GSON_KEY) == true) {
            val string = savedInstanceState.get(Constants.GSON_KEY) as String
            val theList = convertFromStringToImageList(string)
            imageListOffline = theList
            initializeImageList(theList)
        }
        super.onViewStateRestored(savedInstanceState)
    }

    private fun convertFromImageListToString(imageList: ArrayList<Image>): String {
        return GsonBuilder().create().toJson(imageList)
    }

    private fun convertFromStringToImageList(string: String): ArrayList<Image> {
        val token: TypeToken<ArrayList<Image>> = object : TypeToken<ArrayList<Image>>() {}
        return GsonBuilder().create().fromJson(string, token.type)
    }


}