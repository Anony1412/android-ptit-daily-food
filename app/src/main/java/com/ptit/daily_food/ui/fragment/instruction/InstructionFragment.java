package com.ptit.daily_food.ui.fragment.instruction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ptit.daily_food.R;
import com.ptit.daily_food.data.model.Instruction;
import com.ptit.daily_food.ui.base.BaseFragment;

public class InstructionFragment extends BaseFragment {

    private static final String ARGUMENT_INSTRUCTION_ITEM = "ARGUMENT_INSTRUCTION_ITEM";

    private Instruction instruction;

    @Override
    protected void initView() {

    }

    @Override
    protected void initLayout() {
        this.layoutRes = R.layout.fragment_item_instruction;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initActionBar() {

    }

    @Override
    protected void getArgument() {
        Bundle bundle = getArguments();
        instruction = bundle.getParcelable(ARGUMENT_INSTRUCTION_ITEM);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            textInstructionNumber = getActivity().findViewById(R.id.textInstructionNumber);
            textInstructionDes = getActivity().findViewById(R.id.textInstructionDes);
            imageFoodInstruction = getActivity().findViewById(R.id.imageFoodInstruction);
            imageFoodIngredient = getActivity().findViewById(R.id.imageFoodIngredient);
        }
        displayStep();
    }

    private TextView textInstructionNumber, textInstructionDes;
    private ImageView imageFoodInstruction, imageFoodIngredient;

    private void displayStep() {

        if (instruction != null) {
            textInstructionNumber.setText(instruction.getStepNumber());
            textInstructionDes.setText(instruction.getStep());
            String imageInstructionUrl =
                    createImageUrl(instruction.getImageInstruction(), "equipment_250x250");
            String imageIngredientUrl =
                    createImageUrl(instruction.getImageIngredient(), "ingredients_100x100");

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.transform(new CenterCrop(), new RoundedCorners(16));

            Glide.with(getContext())
                    .load(imageInstructionUrl)
                    .apply(requestOptions)
                    .placeholder(R.drawable.ic_broken_image)
                    .into(imageFoodInstruction);

            Glide.with(getContext())
                    .load(imageIngredientUrl)
                    .apply(requestOptions)
                    .placeholder(R.drawable.ic_broken_image)
                    .into(imageFoodIngredient);
        }
    }

    private String createImageUrl(String imageName, String path) {
        if (imageName.isEmpty()) {
            return "https://png.pngtree.com/png-clipart/20190516/original/" +
                    "pngtree-cartoon-chef-hold-dish-png-image_3707878.jpg";
        } else {
            return "https://spoonacular.com/cdn/" + path + "/" + imageName;
        }
    }

    public static InstructionFragment newInstance(Instruction instruction) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_INSTRUCTION_ITEM, instruction);
        InstructionFragment instructionFragment = new InstructionFragment();
        instructionFragment.setArguments(bundle);
        return instructionFragment;
    }
}
