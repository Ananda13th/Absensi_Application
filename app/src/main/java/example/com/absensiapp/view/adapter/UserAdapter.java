package example.com.absensiapp.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding.UserListBinding;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.view.listener.UserRecycleListener;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> implements UserRecycleListener {

    private List<UserModel> userList = new ArrayList<>();
    private UserRecycleListener clickListener;
    private UserAdapter userAdapter = this;

    public void setUserList(List<UserModel> userList) {
        this.userList = userList;

        for(Iterator<UserModel> iterator = userList.iterator();iterator.hasNext();) {
            UserModel user = iterator.next();
            if(user.getUserId().equals("admin")) {
                iterator.remove();
            }
        }
        notifyDataSetChanged();
    }

    public void setOnClick(UserRecycleListener listener) {

        this.clickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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
    public void onClickDeleteButton(String userId) {
        clickListener.onClickDeleteButton(userId);
    }

}
