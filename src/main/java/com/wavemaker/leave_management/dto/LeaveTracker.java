package com.wavemaker.leave_management.dto;

import com.wavemaker.leave_management.enums.LeaveName;

public class LeaveTracker {
    private LeaveName leaveName;
    private int usedLeaves;
    private int leavesLeft;
    private int totalLeaves;

    public LeaveName getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(LeaveName leaveName) {
        this.leaveName = leaveName;
    }

    public int getUsedLeaves() {
        return usedLeaves;
    }

    public void setUsedLeaves(int usedLeaves) {
        this.usedLeaves = usedLeaves;
    }

    public int getLeavesLeft() {
        return leavesLeft;
    }

    public void setLeavesLeft(int leavesLeft) {
        this.leavesLeft = leavesLeft;
    }

    public int getTotalLeaves() {
        return totalLeaves;
    }

    public void setTotalLeaves(int totalLeaves) {
        this.totalLeaves = totalLeaves;
    }

    @Override
    public String toString() {
        return "LeaveTracker{" +
                "leaveName=" + leaveName +
                ", usedLeaves=" + usedLeaves +
                ", leavesLeft=" + leavesLeft +
                ", totalLeaves=" + totalLeaves +
                '}';
    }
}
