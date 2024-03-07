package android.zybooks.studentprogressapplication;

import java.util.ArrayList;
import java.util.List;

public class TermDatabase {


    private List<Term> mTerms;


    public TermDatabase() {
        mTerms = new ArrayList<>();
    }

    public List<Term> getTerms() {
        return mTerms;
    }

    public void addTerm(Term term) {
        mTerms.add(term);
    }

    public Term getTerm(int termId) {
        for (Term term : mTerms) {
            if (term.getId() == termId) {
                return term;
            }
        }
        return null;
    }
}
