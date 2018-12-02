package com.framgia.vhlee.themoviedb.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.data.model.Cast;
import com.framgia.vhlee.themoviedb.data.model.Company;
import com.framgia.vhlee.themoviedb.databinding.ItemCompanyBinding;

import java.util.ArrayList;
import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder> {
    private List<Company> mCompanies;
    private CompanyClickListener mCompanyClickListener;

    public CompanyAdapter(CompanyClickListener listener) {
        mCompanies = new ArrayList<>();
        mCompanyClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCompanyBinding itemCompanyBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_company, parent, false);
        return new ViewHolder(itemCompanyBinding, mCompanyClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mCompanies.get(position));
    }

    @Override
    public int getItemCount() {
        return mCompanies != null ? mCompanies.size() : 0;
    }

    public void update(List<Company> companies) {
        if (mCompanies != null) mCompanies.clear();
        mCompanies.addAll(companies);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemCompanyBinding mItemCompanyBinding;
        private CompanyClickListener mClickListener;

        public ViewHolder(ItemCompanyBinding itemCompanyBinding, CompanyClickListener listener) {
            super(itemCompanyBinding.getRoot());
            mItemCompanyBinding = itemCompanyBinding;
            mClickListener = listener;
            mItemCompanyBinding.linearCompany.setOnClickListener(this);
        }

        public void bindData(Company company) {
            mItemCompanyBinding.setCompany(company);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onCompanyClick(mItemCompanyBinding.getCompany());
        }
    }

    public interface CompanyClickListener {
        void onCompanyClick(Company company);
    }
}
