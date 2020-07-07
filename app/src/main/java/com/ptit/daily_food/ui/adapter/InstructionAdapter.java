package com.ptit.daily_food.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ptit.daily_food.ui.fragment.instruction.InstructionFragment;

import java.util.ArrayList;
import java.util.List;

public class InstructionAdapter extends FragmentPagerAdapter {

    private ArrayList<InstructionFragment> instructionFragments = new ArrayList<>();

    public InstructionAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public InstructionFragment getItem(int position) {
        return instructionFragments.get(position);
    }

    @Override
    public int getCount() {
        return instructionFragments.size();
    }

    public void addFragment(List<InstructionFragment> fragments) {
        instructionFragments = new ArrayList<InstructionFragment>(fragments);
    }
}
