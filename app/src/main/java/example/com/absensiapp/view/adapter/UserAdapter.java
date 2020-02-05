package example.com.absensiapp.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.UserListBinding;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.view.listener.RecycleListener;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> implements RecycleListener {

    private List<UserModel> userList = new ArrayList<>();
    private RecycleListener clickListener;

    public void setUserList(List<UserModel> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    public void setOnClick(RecycleListener listener) {

        this.clickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        UserListBinding listBinding;

        private ViewHolder(UserListBinding listBinding) {
            super(listBinding.getRoot());
            this.listBinding = listBinding;
        }
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserListBinding listBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.user_list, parent, false);
        return new ViewHolder(listBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        UserModel user = userList.get(position);
        holder.listBinding.setUser(user);
        holder.listBinding.setOnClick(this);
    }

    @Override
    public int getItemCount() {
        if(userList != null)
            return userList.size();
        else
            return 0;
    }

    @Override
    public void onClickCardView(UserModel userModel) {
        Log.d("TEST", userModel.toString());
        clickListener.onClickCardView(userModel);
    }

    @Override
    public void onClickDeleteButton(String userId) {
        Log.d("TEST", userId);
        clickListener.onClickDeleteButton(userId);
    }

    @Override
    public void onClickUpdateButton(UserModel userModel) {
        Log.d("TEST", userModel.toString());
        clickListener.onClickUpdateButton(userModel);
    }

}
