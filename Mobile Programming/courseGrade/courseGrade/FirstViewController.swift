//
//  FirstViewController.swift
//  courseGrade
//
//  Created by Carlos Alpuche on 2/5/17.
//  Copyright © 2017 Carlos Alpuche. All rights reserved.
//

import UIKit

class FirstViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    @IBOutlet weak var miniProjectPercentage: UILabel!
    @IBOutlet weak var homeworkPercentage: UILabel!
    @IBOutlet weak var attendencePercentage: UILabel!
    @IBOutlet weak var midtermPercentage: UILabel!
    @IBOutlet weak var finalProjectPercentage: UILabel!
    
    @IBOutlet weak var sldrMiniProjectOutlet: UISlider!
    @IBOutlet weak var sldrHomeworkOutlet: UISlider!
    @IBOutlet weak var sldrAttendenceOutlet: UISlider!
    @IBOutlet weak var sldrMidTermOutlet: UISlider!
    @IBOutlet weak var sldrFinalProjectOutlet: UISlider!
    
    @IBOutlet weak var lblFinalGrade: UILabel!
    @IBAction func sldrMiniProjectAction(_ sender: Any) {
        miniProjectPercentage.text = String(Int(sldrMiniProjectOutlet.value))
        lblFinalGrade.text = gradeClasses()
    }
    
    @IBAction func sldrHomeworkAction(_ sender: Any) {
        homeworkPercentage.text = String(Int(sldrHomeworkOutlet.value))

        lblFinalGrade.text = gradeClasses()
    }
    
    @IBAction func sldrAttendenceAction(_ sender: Any) {
        attendencePercentage.text = String(Int(sldrAttendenceOutlet.value))

        lblFinalGrade.text = gradeClasses()
    }
    
    @IBAction func sldrMidTermAction(_ sender: Any) {
        midtermPercentage.text = String(Int(sldrMidTermOutlet.value))
        lblFinalGrade.text = gradeClasses()
    }
    
    @IBAction func sldrFinalProjectAction(_ sender: Any) {
        finalProjectPercentage.text = String(Int(sldrFinalProjectOutlet.value))

        lblFinalGrade.text = gradeClasses()
    }
    func gradeClasses() -> String{
        var grade = Int(miniProjectPercentage.text!)! + Int(homeworkPercentage.text!)! + Int(attendencePercentage.text!)! + Int(midtermPercentage.text!)! + Int(finalProjectPercentage.text!)!
        
        if(grade < 70){
            return "F"
        }
        else if(grade >= 70 && grade < 80){
            return "C"
        }
        else if(grade >= 80 && grade < 90){
            return "B"
        }
        else{
            return "A"
        }
        
        
    }
    
    
    

}

