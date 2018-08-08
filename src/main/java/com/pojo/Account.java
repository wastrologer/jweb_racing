package com.pojo;

import java.sql.Timestamp;

public class Account {
    private Integer accountId;
    private Integer userId;
    private Integer totalGoldAccount;
    private Integer totalCoinAccount;
    private Integer allowWithdrawal;
    private Integer withdrawalFreeze;
    private Timestamp createTime;
    private Timestamp lastUpdateTime;
    private Integer allowCoin;

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userId=" + userId +
                ", totalGoldAccount=" + totalGoldAccount +
                ", totalCoinAccount=" + totalCoinAccount +
                ", allowWithdrawal=" + allowWithdrawal +
                ", withdrawalFreeze=" + withdrawalFreeze +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", allowCoin=" + allowCoin +
                '}';
    }

    public Integer getAllowCoin() {
        return allowCoin;
    }

    public void setAllowCoin(Integer allowCoin) {
        this.allowCoin = allowCoin;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTotalGoldAccount() {
        return totalGoldAccount;
    }

    public void setTotalGoldAccount(Integer totalGoldAccount) {
        this.totalGoldAccount = totalGoldAccount;
    }

    public Integer getTotalCoinAccount() {
        return totalCoinAccount;
    }

    public void setTotalCoinAccount(Integer totalCoinAccount) {
        this.totalCoinAccount = totalCoinAccount;
    }

    public Integer getAllowWithdrawal() {
        return allowWithdrawal;
    }

    public void setAllowWithdrawal(Integer allowWithdrawal) {
        this.allowWithdrawal = allowWithdrawal;
    }

    public Integer getWithdrawalFreeze() {
        return withdrawalFreeze;
    }

    public void setWithdrawalFreeze(Integer withdrawalFreeze) {
        this.withdrawalFreeze = withdrawalFreeze;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
