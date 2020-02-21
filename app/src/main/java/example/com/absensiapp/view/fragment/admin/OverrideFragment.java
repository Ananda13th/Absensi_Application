package example.com.absensiapp.view.fragment.admin;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.FragmentOverrideBinding;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.OverrideRespListModel;
import example.com.absensiapp.model.OverrideRespModel;
import example.com.absensiapp.view.adapter.OverrideListAdapter;
import example.com.absensiapp.view.listener.OverrideRecycleListener;
import example.com.absensiapp.viewmodel.OverrideViewModel;
import lombok.SneakyThrows;


public class OverrideFragment extends Fragment{

    private FragmentOverrideBinding overrideListBinding;
    private OverrideViewModel overrideViewModel = new OverrideViewModel();
    private OverrideListAdapter overrideAdapter = new OverrideListAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        overrideListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_override, container, false);
        return overrideListBinding.getRoot();
    }

    @SneakyThrows
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        overrideViewModel = ViewModelProviders.of(requireActivity()).get(OverrideViewModel.class);
        setRecycleView();
        setOverrideList();
        overrideAdapter.setOnClick(new OverrideRecycleListener() {
            @Override
            public void onClickAcceptButton(OverrideRespModel overrideRespModel) {
                overrideListBinding.getOverrideList();
                overrideViewModel.acceptOverride(overrideRespModel);
            }

            @Override
            public void onClickRejectButoon() {

            }
        });
        acceptOverrideObserver();
    }

    private void setOverrideList() {
        overrideViewModel.getOverrideList().observe(this, new Observer<OverrideRespListModel>() {
            @Override
            public void onChanged(OverrideRespListModel overrideRespListModel) {
                overrideAdapter.setOverrideList(overrideRespListModel.getOverrideList());

            }
        });
    }

    private void acceptOverrideObserver() {
        Activity activity = getActivity();
        overrideViewModel.getBaseResp().observe(requireActivity(), new Observer<BaseResponseModel>() {
            @Override
            public void onChanged(BaseResponseModel baseResponseModel) {
                Toast.makeText(activity, baseResponseModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setRecycleView() {
        RecyclerView recyclerView = requireView().findViewById(R.id.recycler_view_override);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(overrideAdapter);
    }
}
