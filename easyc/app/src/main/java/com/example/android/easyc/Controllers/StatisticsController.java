package com.example.android.easyc.Controllers;

import com.example.android.easyc.Interfaces.OnTaskListeners;
import com.example.android.easyc.Models.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by KhALeD SaBrY on 09-May-18.
 */

public class StatisticsController extends Controller {


    public UserData getUserData()
    {
        return userData();
    }

    //get number of students
    public void getNumberOfStudent(final OnTaskListeners.Number listener)
    {
        databaseAdapter().selectUserCountByType('S', new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                Integer result = null;

                try {
                    data.next();
                    result = data.getInt("result");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                listener.onSuccess(result);
            }
        });
    }

    //get number of instructors
    public void getNumberOfInstructors(final OnTaskListeners.Number listener)
    {
        databaseAdapter().selectUserCountByType('I', new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if(!checkIfFound(data))
                    return;
                Integer result = null;

                try {
                    data.next();
                    result = data.getInt("result");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                listener.onSuccess(result);
            }
        });
    }

    //get number of opinions
    public void getNumberOfOpinions(final OnTaskListeners.Number listener)
    {
        databaseAdapter().selectOpinionCount(new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if(!checkIfFound(data))
                    return;
                Integer result = null;

                try {
                    data.next();
                    result = data.getInt("result");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                listener.onSuccess(result);
            }
        });
    }

    //get number of reports
    public void getNumberOfReports(final OnTaskListeners.Number listener)
    {
        databaseAdapter().selectReportCount(new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if(!checkIfFound(data))
                    return;
                Integer result = null;

                try {
                    data.next();
                    result = data.getInt("result");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                listener.onSuccess(result);
            }
        });
    }

    //get the average grade for the students
    public void getTheAverageGradeForStudents(final OnTaskListeners.Number listener)
    {
        databaseAdapter().selectExamAvgGrade(new OnTaskListeners.Result() {
            @Override
            public void onSuccess(ResultSet data) {
                if(!checkIfFound(data))
                    return;
                Integer result = null;

                try {
                    data.next();
                    result = data.getInt("result");
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                listener.onSuccess(result);
            }
        });
    }
}
