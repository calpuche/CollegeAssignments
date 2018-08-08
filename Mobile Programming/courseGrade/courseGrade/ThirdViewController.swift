//
//  ThirdViewController.swift
//  courseGrade
//
//  Created by Carlos Alpuche on 2/5/17.
//  Copyright © 2017 Carlos Alpuche. All rights reserved.
//

import UIKit

class ThirdViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    


    @IBOutlet weak var prelabPercentage: UILabel!
    @IBOutlet weak var quizzesPercentage: UILabel!
    @IBOutlet weak var labPercentage: UILabel!
    
    @IBOutlet weak var sldrAsgOutlet: UISlider!
    
    @IBOutlet weak var sldrQuizOutlet: UISlider!
    
    @IBOutlet weak var sldrLabOutlet: UISlider!
    
    @IBOutlet weak var lblFinalGrade: UILabel!
    
    
    
    @IBAction func sldrAsgAction(_ sender: Any) {
        prelabPercentage.text = String(Int(sldrAsgOutlet.value))
        lblFinalGrade.text = gradeClasses()
    }

    @IBAction func sldrQuizAction(_ sender: Any) {
        quizzesPercentage.text = String(Int(sldrQuizOutlet.value))
        lblFinalGrade.text = gradeClasses()
    }
   
    @IBAction func sldrLabAction(_ sender: Any) {
        labPercentage.text = String(Int(sldrLabOutlet.value))
        lblFinalGrade.text = gradeClasses()
    }
    func gradeClasses() -> String{
         var grade = Int(prelabPercentage.text!)! + Int(quizzesPercentage.text!)! + Int(labPercentage.text!)!
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
