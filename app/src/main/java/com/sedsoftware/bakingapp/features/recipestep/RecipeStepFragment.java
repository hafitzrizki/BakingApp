package com.sedsoftware.bakingapp.features.recipestep;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.sedsoftware.bakingapp.R;
import com.sedsoftware.bakingapp.data.model.Step;
import java.util.ArrayList;
import java.util.List;

public class RecipeStepFragment extends Fragment implements RecipeStepContract.View {

  @BindView(R.id.recipe_step_viewpager)
  ViewPager recipeStepViewPager;
  Unbinder unbinder;

  private RecipeStepContract.Presenter recipeStepPresenter;
  private RecipeStepPageAdapter viewPagerAdapter;

  private int stepId;

  public RecipeStepFragment() {
  }

  public static RecipeStepFragment newInstance(int stepId) {
    Bundle arguments = new Bundle();
    arguments.putInt(RecipeStepActivity.EXTRA_STEP_ID, stepId);
    RecipeStepFragment fragment = new RecipeStepFragment();
    fragment.setArguments(arguments);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    stepId = getArguments().getInt(RecipeStepActivity.EXTRA_STEP_ID);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);
    unbinder = ButterKnife.bind(this, view);

    viewPagerAdapter = new RecipeStepPageAdapter(getFragmentManager(), new ArrayList<>(0));
    recipeStepViewPager.setAdapter(viewPagerAdapter);

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    recipeStepPresenter.subscribe();
  }

  @Override
  public void onPause() {
    super.onPause();
    recipeStepPresenter.unsubscribe();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void setPresenter(RecipeStepContract.Presenter presenter) {
    this.recipeStepPresenter = presenter;
  }

  @Override
  public void showStepsInViewpager(List<Step> steps) {
    viewPagerAdapter.setSteps(steps);
  }

  @Override
  public void showErrorMessage() {
    // TODO(3) Implement this
  }

  @Override
  public void moveToCurrentStepPage() {
    recipeStepViewPager.setCurrentItem(stepId);
  }
}