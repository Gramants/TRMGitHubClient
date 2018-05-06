package demo.ste.mvpcleanarch.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import demo.ste.mvpcleanarch.R;
import demo.ste.mvpcleanarch.model.entities.RepoResponseDbEntity;


public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.ViewHolder> {

    private final List<RepoResponseDbEntity> items;
    private final OnItemClickListener listener;

    public void clearAll() {
        this.items.clear();
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(RepoResponseDbEntity item);
    }

    public RepoListAdapter(OnItemClickListener listener) {
        this.items = new ArrayList();
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindItem(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void updateRepoList(List<RepoResponseDbEntity> repos) {
        this.items.clear();
        this.items.addAll(repos);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private RelativeLayout mContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            mContainer = (RelativeLayout) itemView.findViewById(R.id.container);
            mTitle = (TextView) itemView.findViewById(R.id.tv_name);

        }

        public void bindItem(RepoResponseDbEntity item, OnItemClickListener listener) {
            mTitle.setText(item.getName());

            mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }
    }


}