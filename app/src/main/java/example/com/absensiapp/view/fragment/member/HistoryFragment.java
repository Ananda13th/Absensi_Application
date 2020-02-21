package example.com.absensiapp.view.fragment.member;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.FragmentHistoryBinding;
import example.com.absensiapp.model.InputHistoryModel;
import example.com.absensiapp.model.OutputHistoryModel;
import example.com.absensiapp.view.adapter.HistoryAdapter;
import example.com.absensiapp.view.listener.HistoryRecycleListener;
import example.com.absensiapp.view.utils.MonthFormatter;
import example.com.absensiapp.viewmodel.HistoryViewModel;
import lombok.SneakyThrows;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HistoryFragment extends Fragment implements HistoryRecycleListener {

    private FragmentHistoryBinding historyBinding;
    private HistoryViewModel historyViewModel = new HistoryViewModel();
    private InputHistoryModel inputHistoryModel = new InputHistoryModel();
    private MonthFormatter monthFormatter = new MonthFormatter();
    private HistoryAdapter historyAdapter = new HistoryAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        historyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        return historyBinding.getRoot();
    }

    @SneakyThrows
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        historyViewModel = ViewModelProviders.of(requireActivity()).get(HistoryViewModel.class);
        SharedPreferences sharedPreferences = this.requireActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("UserId", "");
        inputHistoryModel.setUserId(userId);
        String month = monthFormatter.StringToNumberMonth(historyBinding.monthSpinner.getSelectedItem().toString());
        inputHistoryModel.setMonth(month);
        historyBinding.setInputHistory(inputHistoryModel);
        historyBinding.setOnClick(this);
        setRecycleView();
        setHistory();
        historyObserver();
    }
    @Override
    public void onClickSearchButton(InputHistoryModel inputHistoryModel) {
        historyBinding.getInputHistory();
        historyViewModel.loadHistory(inputHistoryModel);
    }

    private void historyObserver() {
        historyViewModel.getHistory().observe(this, new Observer<OutputHistoryModel>() {
            @Override
            public void onChanged(OutputHistoryModel outputHistoryModel) {
                Log.d("History", outputHistoryModel.getErrorMessage());
            }
        });
    }

    private void setHistory() {
        historyViewModel.getHistory().observe(requireActivity(), new Observer<OutputHistoryModel>() {
            @Override
            public void onChanged(OutputHistoryModel outputHistoryModel) {
                historyAdapter.setHistoryList(outputHistoryModel.getHistData());
                Log.d("TET", outputHistoryModel.toString());
                historyBinding.tvNumberOfAttend.setText(outputHistoryModel.getOutputAttend());
            }
        });
    }

    private void setRecycleView() {
        RecyclerView recyclerView = requireView().findViewById(R.id.recycler_view_history);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(historyAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        //historyViewModel.resetHistory();
    }
}
