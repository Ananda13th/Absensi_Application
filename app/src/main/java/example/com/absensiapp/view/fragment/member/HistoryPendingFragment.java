package example.com.absensiapp.view.fragment.member;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.ListOverrideHistoryBinding;
import example.com.absensiapp.model.BaseResponseModel;
import example.com.absensiapp.model.OverrideHistoryRespListModel;
import example.com.absensiapp.view.adapter.OverrideHistoryAdapter;
import example.com.absensiapp.view.listener.OverrideHistoryListener;
import example.com.absensiapp.viewmodel.OverrideViewModel;

public class HistoryPendingFragment extends Fragment {
    private OverrideViewModel overrideViewModel = new OverrideViewModel();
    private String userid;
    private OverrideHistoryAdapter overrideHistoryAdapter = new OverrideHistoryAdapter("Diproses");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_override_pending, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = this.requireActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        userid = sharedPreferences.getString("UserId", "");
        setOverrideHistory();
        setRecycleView();
        overrideHistoryAdapter.setOnClick(new OverrideHistoryListener() {
            @Override
            public void onClickDeleteButton(String overrideId) {
                deleteConfirmation(overrideId);
            }
        });
        overrideObserver();
    }

    private void setOverrideHistory() {
        overrideViewModel.getOverrideHistory(userid).observe(this, new Observer<OverrideHistoryRespListModel>() {
            @Override
            public void onChanged(OverrideHistoryRespListModel overrideHistoryRespListModel) {
                overrideHistoryAdapter.setOverrideHistory(overrideHistoryRespListModel.getOverrideReqList());
            }
        });
    }

    private void setRecycleView() {
        RecyclerView recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.override_history_recycleview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(overrideHistoryAdapter);
    }


    private void overrideObserver() {
        overrideViewModel.getBaseResp().observe(getViewLifecycleOwner(), new Observer<BaseResponseModel>() {
            @Override
            public void onChanged(BaseResponseModel baseResponseModel) {
                if(null != baseResponseModel.getErrorMessage())
                    Toast.makeText(getActivity(), baseResponseModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteConfirmation(final String id) {
        AlertDialog deleteDialog = new AlertDialog.Builder(requireActivity())
                .setTitle("Hapus Override")
                .setMessage("Anda Yakin Ingin Menghapus Override Ini?")
                .setPositiveButton("Hapus", (dialog, whichButton) -> overrideViewModel.deletePendingOverride(id))
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        deleteDialog.show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        overrideViewModel.clearViewModelValue();
    }
}
