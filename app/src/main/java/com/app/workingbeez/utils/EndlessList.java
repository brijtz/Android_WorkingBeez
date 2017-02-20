package com.app.workingbeez.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class EndlessList {

    int firstVisibleItem, visibleItemCount, totalItemCount;
    OnLoadMoreListener loadMoreListener;
    private boolean isLock = false;
    public boolean enable = true;
    private boolean stackFromEnd = false;
    private RecyclerView.LayoutManager layoutManager;

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (enable) {
                if (stackFromEnd) {

                    if(layoutManager instanceof LinearLayoutManager){

                        firstVisibleItem = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
                        boolean loadMore = firstVisibleItem == 0;

                        if (loadMore && !isLock) {
                            if (loadMoreListener != null) {
                                loadMoreListener.onLoadMore();
                            }
                        }

                    } else if(layoutManager instanceof GridLayoutManager){

                        firstVisibleItem = ((GridLayoutManager)layoutManager).findFirstVisibleItemPosition();
                        boolean loadMore = firstVisibleItem == 0;

                        if (loadMore && !isLock) {
                            if (loadMoreListener != null) {
                                loadMoreListener.onLoadMore();
                            }
                        }

                    } else if(layoutManager instanceof StaggeredGridLayoutManager){

                        int firstVisibleItem[] = ((StaggeredGridLayoutManager)layoutManager).findFirstVisibleItemPositions(null);
                        boolean loadMore = firstVisibleItem[0] == 0 && firstVisibleItem[1]==0;

                        if (loadMore && !isLock) {
                            if (loadMoreListener != null) {
                                loadMoreListener.onLoadMore();
                            }
                        }
                    }



                } else {

                    if(layoutManager instanceof LinearLayoutManager){

                        visibleItemCount = recyclerView.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        firstVisibleItem = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
                        if (totalItemCount != visibleItemCount) {
                            boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;

                            if (loadMore && !isLock) {
                                if (loadMoreListener != null) {
                                    loadMoreListener.onLoadMore();
                                }
                            }
                        }

                    } else if(layoutManager instanceof GridLayoutManager){

                        visibleItemCount = recyclerView.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        firstVisibleItem = ((GridLayoutManager)layoutManager).findFirstVisibleItemPosition();
                        if (totalItemCount != visibleItemCount) {
                            boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;

                            if (loadMore && !isLock) {
                                if (loadMoreListener != null) {
                                    loadMoreListener.onLoadMore();
                                }
                            }
                        }

                    } else if(layoutManager instanceof StaggeredGridLayoutManager){

                        visibleItemCount = recyclerView.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        int firstVisibleItemArray[] = ((StaggeredGridLayoutManager)layoutManager).findFirstVisibleItemPositions(null);
                        if (totalItemCount != visibleItemCount) {
                            boolean loadMore = firstVisibleItemArray[0] + visibleItemCount >= totalItemCount;

                            if (loadMore && !isLock) {
                                if (loadMoreListener != null) {
                                    loadMoreListener.onLoadMore();
                                }
                            }
                        }

                        int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
                        // get maximum element within the list
                        firstVisibleItem = getLastVisibleItem(lastVisibleItemPositions);

                        boolean loadMore = firstVisibleItem == 0;

                        if (loadMore && !isLock) {
                            if (loadMoreListener != null) {
                                loadMoreListener.onLoadMore();
                            }
                        }
                    }


//                    visibleItemCount = recyclerView.getChildCount();
//                    totalItemCount = layoutManager.getItemCount();
//                    firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
//                    if (totalItemCount != visibleItemCount) {
//                        boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
//
//                        if (loadMore && !isLock) {
//                            if (loadMoreListener != null) {
//                                loadMoreListener.onLoadMore();
//                            }
//                        }
//                    }
                }
            }
        }

    };

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            }
            else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    private RecyclerView recyclerView;

    public EndlessList(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        this.layoutManager = linearLayoutManager;
        this.recyclerView = recyclerView;
        this.recyclerView.setOnScrollListener(onScrollListener);
    }

    public EndlessList(RecyclerView recyclerView, GridLayoutManager linearLayoutManager) {
        this.layoutManager = linearLayoutManager;
        this.recyclerView = recyclerView;
        this.recyclerView.setOnScrollListener(onScrollListener);
    }

    public EndlessList(RecyclerView recyclerView, StaggeredGridLayoutManager linearLayoutManager) {
        this.layoutManager = linearLayoutManager;
        this.recyclerView = recyclerView;
        this.recyclerView.setOnScrollListener(onScrollListener);
    }

    public void setStackFromEnd(boolean stackFromEnd) {
        this.stackFromEnd = stackFromEnd;
    }

    public void lockMoreLoading() {
        isLock = true;
    }

    public void releaseLock() {
        isLock = false;
    }

    public void disableLoadMore() {
        enable = false;
    }

    public void enableLoadMore() {
        enable = true;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

}