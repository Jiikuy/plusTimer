package com.pluscubed.plustimer.model;

import com.pluscubed.plustimer.Util;

/**
 * solve times data object
 */
public class Solve {
    private ScrambleAndSvg mScrambleAndSvg;
    private Penalty mPenalty;
    private long mRawTime;
    private long mTimestamp;

    public Solve(ScrambleAndSvg scramble, long time) {
        mScrambleAndSvg = scramble;
        mRawTime = time;
        mPenalty = Penalty.NONE;
        mTimestamp = System.currentTimeMillis();
    }

    public ScrambleAndSvg getScrambleAndSvg() {
        return mScrambleAndSvg;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public long getTimeTwo() {
        if (mPenalty == Penalty.PLUSTWO) {
            return mRawTime + 2000000000L;
        }
        return mRawTime;
    }

    public String getTimeString() {
        switch (mPenalty) {
            case DNF:
                return "DNF";
            case PLUSTWO:
                return Util.timeStringFromNanoseconds(mRawTime + 2000000000L) + "+";
            case NONE:
            default:
                return Util.timeStringFromNanoseconds(mRawTime);
        }
    }

    public String getDescriptiveTimeString() {
        switch (mPenalty) {
            case DNF:
                if (mRawTime != 0) return "DNF (" + Util.timeStringFromNanoseconds(mRawTime) + ")";
            default:
                return getTimeString();
        }
    }

    public Penalty getPenalty() {
        return mPenalty;
    }

    public void setPenalty(Penalty penalty) {
        mPenalty = penalty;
    }

    public void setRawTime(long time) {
        this.mRawTime = time;
    }

    public enum Penalty {
        NONE, PLUSTWO, DNF
    }


}
