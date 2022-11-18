package edu.uncc.inclass12;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.uncc.inclass12.databinding.FragmentGradesBinding;
import edu.uncc.inclass12.databinding.GradeRowItemBinding;

public class GradesFragment extends Fragment {
    FragmentGradesBinding binding;
    GradeDAO gradeDAO;
    DataBaseManager dm;
    GradeAdapter gradeAdapter;
    ArrayList<Grade> mGrade = new ArrayList<>();
    GradeListener mListener;

    public GradesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = FragmentGradesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
/*    void getGrade(){

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        db.collection("courses")
                .document(currentUser.getUid()).collection("userCourse")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        mGrade.clear();
                        for (QueryDocumentSnapshot document : value) {
                            if (document.getData().size()>0) {
                                Grade grade = new Grade();
                                Log.d("Demo", document.getId() + " => " + document.getData());
                                grade.setCourseName(document.getData().get("courseName").toString());
                                grade.setCourseNo(document.getData().get("courseNo").toString());
                                grade.setCreditHr(document.getData().get("creditHr").toString());
                                grade.setGrade(document.getData().get("grade").toString());
                                grade.setCourseId(document.getId());


                                mGrade.add(grade);
                            }
                        }
                        calculateGPA();
                        gradeAdapter.notifyDataSetChanged();
                    }
                });



    }*/

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                // navigate to settings screen
                  mListener.addGradeFragment();
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dm = new DataBaseManager(getActivity());
        binding.recyclerViewGrade.setLayoutManager(new LinearLayoutManager(getContext()));
        gradeAdapter = new GradeAdapter();
        binding.recyclerViewGrade.setAdapter(gradeAdapter);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // getGrade();
                 getAll();

                gradeAdapter.notifyDataSetChanged();

            }
        });


        getActivity().setTitle(R.string.posts_label);

    }

    void getAll(){
        mGrade.clear();
        ArrayList<Grade> all = dm.getGradeDAO().getAll();
        if (all != null) {
            //mGrade.addAll(all);
            //  gradeAdapter.notifyDataSetChanged();
            mGrade.addAll(all);
            calculateGPA();
        }
        //return ;
    }

    void calculateGPA() {
        double gpa = 0.0;
        double totalHr = 0;
        int gradePointTotal = 0;
        for (Grade grade : mGrade) {
            totalHr = totalHr + grade.getTotalhr();

            gradePointTotal = gradePointTotal + grade.getGradePointTotal();


            grade.setTotalhr((int) totalHr);
            gpa = gradePointTotal / totalHr;
            grade.setGpa(gpa);
        }
        binding.textViewGPA.setText("GPA :" + String.valueOf(gpa).substring(0, 3));
        binding.textViewHr.setText("Total Hr :" + totalHr);
        gradeAdapter.notifyDataSetChanged();

    }

    public void updateDeleteField(String course) {
        dm.getGradeDAO().delete(course);

    }

  /*  public void updateDeleteField(String course) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("courses")
                .document(currentUser.getUid()).collection("userCourse")

                .document(course)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("demo", "DocumentSnapshot successfully deleted!");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                            }
                        });
                        gradeAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("demo", "Error deleting document", e);
                    }
                });
    }*/

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (GradeListener) context;
    }

    interface GradeListener {
        void addGradeFragment();
    }

    class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.PostsViewHolder> {
        @NonNull
        @Override
        public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            GradeRowItemBinding binding = GradeRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new PostsViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
            Grade post = mGrade.get(position);
            holder.setupUI(post);
        }

        @Override
        public int getItemCount() {
            return mGrade.size();
        }

        class PostsViewHolder extends RecyclerView.ViewHolder {
            GradeRowItemBinding mBinding;
            Grade mGrade;

            public PostsViewHolder(GradeRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Grade grade) {
                mGrade = grade;
                mBinding.textViewCourseName.setText(grade.getCourseName());
                mBinding.textViewCourseNumber.setText(grade.getCourseNo());
                mBinding.textViewCourseHours.setText(grade.getCreditHr());

                /*  if(mGrade.getCreated_by_uid().equals(currentUser.getUid())){*/
                mBinding.imageViewDelete.setVisibility(View.VISIBLE);
                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
               /* } else {
                    mBinding.imageViewDelete.setVisibility(View.INVISIBLE);
                }*/

                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateDeleteField(grade.courseId);
                        getAll();
                      //  calculateGPA();
                        gradeAdapter.notifyDataSetChanged();
                    }
                });

            }
        }

    }
}