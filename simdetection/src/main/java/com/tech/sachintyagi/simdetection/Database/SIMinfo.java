package com.tech.sachintyagi.simdetection.Database;

/**
 * Created by SachinTyagi on 9/20/17.
 */

public class SIMinfo {

    //private variables
    int _id;
    String _sim_no;
    String _icc;

    // Empty constructor
    public SIMinfo(){

    }
    // constructor
    public SIMinfo(int id, String _sim_no, String _icc){
        this._id = id;
        this._sim_no = _sim_no;
        this._icc = _icc;
    }

    // constructor
    public SIMinfo(String _sim_no, String _icc){
        this._sim_no = _sim_no;
        this._icc = _icc;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_sim_no() {
        return _sim_no;
    }

    public void set_sim_no(String _sim_no) {
        this._sim_no = _sim_no;
    }

    public String get_icc() {
        return _icc;
    }

    public void set_icc(String _icc) {
        this._icc = _icc;
    }
}
