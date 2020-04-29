package example.com.absensiapp.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import example.com.absensiapp.R;
import example.com.absensiapp.databinding. ListUserBinding;
import example.com.absensiapp.model.UserModel;
import example.com.absensiapp.view.listener.UserRecycleListener;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> implements UserRecycleListener {

    private List<UserModel> userList = new ArrayList<>();
    private UserRecycleListener clickListener;

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

    static class ViewHolder extends RecyclerView.ViewHolder {
        ListUserBinding listBinding;

        private ViewHolder( ListUserBinding listBinding) {
            super(listBinding.getRoot());
            this.listBinding = listBinding;
        }
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListUserBinding listBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_user, parent, false);
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

    @Override
    public void onClickEditButton(UserModel userModel) {
        clickListener.onClickEditButton(userModel);
    }

    @Override
    public void onClickSubmitButton(UserModel userModel) {
        clickListener.onClickSubmitButton(userModel);
    }

}
