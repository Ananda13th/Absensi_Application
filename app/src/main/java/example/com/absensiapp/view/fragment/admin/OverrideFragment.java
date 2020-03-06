package example.com.absensiapp.view.fragment.admin;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.FragmentOverrideBinding;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.OverrideRespListModel;
import example.com.absensiapp.model.OverrideRespModel;
import example.com.absensiapp.view.adapter.OverrideListAdapter;
import example.com.absensiapp.view.listener.OverrideRecycleListener;
import example.com.absensiapp.viewmodel.OverrideViewModel;
import lombok.SneakyThrows;


public class OverrideFragment extends Fragment {

    private FragmentOverrideBinding overrideListBinding;
    private OverrideViewModel overrideViewModel = new OverrideViewModel();
    private OverrideListAdapter overrideAdapter = new OverrideListAdapter();
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        overrideListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_override, container, false);
        context = getActivity();
        return overrideListBinding.getRoot();
    }

    @SneakyThrows
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Daftar Override");
        overrideViewModel = ViewModelProviders.of(requireActivity()).get(OverrideViewModel.class);
        swipeRefreshLayout = overrideListBinding.swipeRefresh;
        setRecycleView();
        setOverrideList();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        setOverrideList();
                    }
                }, 2000);
            }
        });
        overrideAdapter.setOnClick(new OverrideRecycleListener() {
            @Override
            public void onClickAcceptButton(OverrideRespModel overrideRespModel) {
                overrideListBinding.getOverrideList();
                overrideViewModel.acceptOverride(overrideRespModel);
            }

            @Override
            public void onClickRejectButton(OverrideRespModel overrideRespModel) {
                overrideListBinding.getOverrideList();
                deleteConfirmation(overrideRespModel);

            }
        });
        overrideObserver();
    }

    private void setOverrideList() {
        overrideViewModel.getOverrideList().observe(this, new Observer<OverrideRespListModel>() {
            @Override
            public void onChanged(OverrideRespListModel overrideRespListModel) {
                overrideAdapter.setOverrideList(overrideRespListModel.getOverrideList());

            }
        });
    }

    private void overrideObserver() {

        final Observer<BaseResponseModel> observer = new Observer<BaseResponseModel>() {
            @Override
            public void onChanged(BaseResponseModel baseResponseModel) {
                Toast.makeText(context, baseResponseModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
                overrideViewModel.getBaseResp().removeObserver(this);
            }
        };

        overrideViewModel.getBaseResp().observe(requireActivity(), observer);



//        overrideViewModel.getBaseResp().observe(requireActivity(), new Observer<BaseResponseModel>() {
//            @Override
//            public void onChanged(BaseResponseModel baseResponseModel) {
//                Toast.makeText(context, baseResponseModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if(overrideViewModel != null && overrideViewModel.getBaseResp().hasObservers())
//            overrideViewModel.getBaseResp().removeObserver(this);
    }

    private void setRecycleView() {
        RecyclerView recyclerView = requireView().findViewById(R.id.recycler_view_override);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(overrideAdapter);
    }

    private void deleteConfirmation(OverrideRespModel overrideRespModel) {
        AlertDialog deleteDialog = new AlertDialog.Builder(requireActivity())
                .setTitle("REJECT")
                .setMessage("Do you want to Reject")
                .setPositiveButton("Reject", (dialog, whichButton) -> overrideViewModel.rejectOverride(overrideRespModel))
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        deleteDialog.show();
    }

}
