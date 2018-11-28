package com.framgia.vhlee.themoviedb.ui.category;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.framgia.vhlee.themoviedb.R;
import com.framgia.vhlee.themoviedb.databinding.ActivityCategoryBinding;

public class CategoryActivity extends AppCompatActivity {
    private static final String EXTRA_ARGS = "args";
    private CategoryViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCategoryBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_category);
        mViewModel = new CategoryViewModel();
        binding.setCategoryVM(mViewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModel.loaMovies();
    }

    public static Intent getCategoryIntent(Context context, Bundle bundle) {
        Intent intent = new Intent(context, CategoryActivity.class);
        intent.putExtra(EXTRA_ARGS, bundle);
        return intent;
    }
}
