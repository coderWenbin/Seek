package wb.app.seek.modules.zhihu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wb.app.seek.R;
import wb.app.seek.common.base.mvp.MvpFragment;
import wb.app.seek.common.widgets.recyclerview.OnRecyclerViewScrollListener;
import wb.app.seek.model.ZhihuDailyNews;
import wb.app.seek.utils.DateTimeUtils;

/**
 * Created by W.b on 2017/2/9.
 */
public class ZhihuDailyFragment extends MvpFragment<ZhihuDailyPresenter> implements ZhihuDailyContract.View, SwipeRefreshLayout.OnRefreshListener {

  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.refresh_layout) SwipeRefreshLayout mRefreshLayout;
  @BindView(R.id.rocket_fab) FloatingActionButton mRocketFab;
  private ZhihuDailyAdapter mZhihuListAdapter;

  public static ZhihuDailyFragment newInstance() {
    return new ZhihuDailyFragment();
  }

  @Override
  protected ZhihuDailyPresenter createPresenter() {
    return new ZhihuDailyPresenter();
  }

  @Override
  protected int getContentViewId() {
    return R.layout.fragment_zhihu;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_zhihu, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  protected void initComponents(View view) {
    initRecyclerView();

    mRefreshLayout.setOnRefreshListener(this);
    mRefreshLayout.post(new Runnable() {
      @Override
      public void run() {
        onRefresh();
      }
    });
  }

  private void initRecyclerView() {
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mZhihuListAdapter = new ZhihuDailyAdapter();
    mZhihuListAdapter.setOnItemClickListener(new ZhihuDailyAdapter.OnItemClickListener() {

      @Override
      public void onClick(int id) {
        Intent intent = new Intent(getActivity(), ZhihuDailyDetailActivity.class);
        intent.putExtra("storyId", id);
        getActivity().startActivity(intent);
      }
    });
    mRecyclerView.setAdapter(mZhihuListAdapter);
    mRecyclerView.addOnScrollListener(onScrollListener);
  }

  private RecyclerView.OnScrollListener onScrollListener = new OnRecyclerViewScrollListener() {


    @Override
    protected void onRefresh(boolean isCanRefresh) {
      mRefreshLayout.setEnabled(isCanRefresh ? true : false);
    }

    @Override
    protected void onLoadMore() {
      getPresenter().loadMoreNews();
    }

    @Override
    public void showRocket() {
      super.showRocket();

      mRocketFab.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRocket() {
      super.hideRocket();

      mRocketFab.setVisibility(View.GONE);
    }
  };

  @Override
  public void showLoading() {
    mRefreshLayout.setRefreshing(true);
  }

  @Override
  public void hideLoading() {
    if (mRefreshLayout.isRefreshing()) {
      mRefreshLayout.setRefreshing(false);
    }
  }

  @Override
  public void showError(String msg, String exception) {
    super.showError(msg, exception);
  }

  @Override
  public void showNews(ZhihuDailyNews dailyNews) {
    mZhihuListAdapter.showNews(dailyNews);
  }

  @Override
  public void showMoreNews(ZhihuDailyNews dailyNews) {
    mZhihuListAdapter.showMoreNews(dailyNews);
  }

  @Override
  public void showNoMoreNews() {
    mZhihuListAdapter.showNoMoreNews();
  }

  @Override
  public void onRefresh() {
    getPresenter().refreshNews(DateTimeUtils.getCurrentDay());
  }

  @OnClick({R.id.rocket_fab})
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.rocket_fab:
        // 快速滚到到顶部
        smoothScrollTop();
//        final Calendar calendar = Calendar.getInstance();
//        int curYear = calendar.get(Calendar.YEAR);
//        int curMonth = calendar.get(Calendar.MONTH);
//        int curDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
//        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//          @Override
//          public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//            smoothScrollTop();
//
//            month += 1;
//            String monthStr = DateTimeUtils.formatDate(month);
//            String dayOfMonthStr = DateTimeUtils.formatDate(dayOfMonth);
//            getPresenter().refreshNews(String.format("%1$d%2$s%3$s", year, monthStr, dayOfMonthStr));
//          }
//        }, curYear, curMonth, curDayOfMonth);
//        datePickerDialog.show();
        break;
    }
  }

  private void smoothScrollTop() {
    mRecyclerView.scrollToPosition(0);
//    mRecyclerView.smoothScrollToPosition(0);
  }
}
