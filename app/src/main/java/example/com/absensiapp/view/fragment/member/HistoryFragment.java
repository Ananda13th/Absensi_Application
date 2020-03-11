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
import example.com.absensiapp.view.utils.UtilsFormatter;
import example.com.absensiapp.viewmodel.HistoryViewModel;
import lombok.SneakyThrows;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryFragment extends Fragment implements HistoryRecycleListener {

    private FragmentHistoryBinding historyBinding;
    private HistoryViewModel historyViewModel = new HistoryViewModel();
    private InputHistoryModel inputHistoryModel = new InputHistoryModel();
    private UtilsFormatter utilsFormatter = new UtilsFormatter();
    private HistoryAdapter historyAdapter = new HistoryAdapter();
    Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        historyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);

        return historyBinding.getRoot();
    }

    @SneakyThrows
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Riwayat Presensi");
        context = getActivity();
        historyViewModel = ViewModelProviders.of(requireActivity()).get(HistoryViewModel.class);
        SharedPreferences sharedPreferences = this.requireActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("UserId", "");
        inputHistoryModel.setUserId(userId);

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int yearBefore = year-1;
        String[] arrayOfYear = new String[] {
                String.valueOf(yearBefore), String.valueOf(year)
        };
        ArrayAdapter<String> yearToSpinner = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, arrayOfYear);
        yearToSpinner.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        historyBinding.spinnerYear.setAdapter(yearToSpinner);
        historyBinding.spinnerYear.setSelection(1);
        historyBinding.spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final String yearValue = historyBinding.spinnerYear.getSelectedItem().toString();
                inputHistoryModel.setYear(yearValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        int indexofmonth = Calendar.getInstance().get(Calendar.MONTH);

        historyBinding.monthSpinner.setSelection(indexofmonth);

        historyBinding.monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final String spinnerValue =historyBinding.monthSpinner.getSelectedItem().toString();
                String month = utilsFormatter.StringToNumberMonth(spinnerValue);
                inputHistoryModel.setMonth(month);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
        historyViewModel.getHistory().observe(getViewLifecycleOwner(), new Observer<OutputHistoryModel>() {
            @Override
            public void onChanged(OutputHistoryModel outputHistoryModel) {
                historyAdapter.setHistoryList(outputHistoryModel.getHistData());
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
