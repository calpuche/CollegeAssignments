//
//  SecondViewController.swift
//  courseGrade
//
//  Created by Carlos Alpuche on 2/5/17.
//  Copyright © 2017 Carlos Alpuche. All rights reserved.
//

import UIKit

class SecondViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBOutlet weak var checkpointsPercentage: UILabel!
    @IBOutlet weak var awarenessPercentage: UILabel!
    @IBOutlet weak var buildingPercentage: UILabel!
    @IBOutlet weak var sldrCheckPointOutlet: UISlider!
    @IBOutlet weak var sldrAwarenessOutlet: UISlider!
    @IBOutlet weak var sldrBuildingOutlet: UISlider!
    @IBOutlet weak var lblFinalGrade: UILabel!
    
    
    
    @IBAction func sldrCheckPointAction(_ sender: Any) {
        checkpointsPercentage.text = String(Int(sldrCheckPointOutlet.value))
        lblFinalGrade.text = gradeClasses()
    }
    
    @IBAction func sldrAwarenessAction(_ sender: Any) {
        awarenessPercentage.text = String(Int(sldrAwarenessOutlet.value))
         lblFinalGrade.text = gradeClasses()
        
        
    }
    
    @IBAction func sldrBuildingAction(_ sender: Any) {
        buildingPercentage.text = String(Int(sldrBuildingOutlet.value))
         lblFinalGrade.text = gradeClasses()
        
        
    }
    func gradeClasses() -> String{
        var grade = Int(checkpointsPercentage.text!)! + Int(awarenessPercentage.text!)! + Int(buildingPercentage.text!)!
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

