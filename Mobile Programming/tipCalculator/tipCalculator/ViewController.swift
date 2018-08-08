//
//  ViewController.swift
//  tipCalculator
//
//  Created by Carlos Alpuche on 1/30/17.
//  Copyright © 2017 Carlos Alpuche. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    
    @IBOutlet weak var billAmount: UITextField!
    
    @IBOutlet weak var segTipPercent: UISegmentedControl!
    
    @IBOutlet weak var lblTipAmount: UILabel!
    
    @IBAction func btnCalculate(_ sender: Any) {
        //.text means that we are getting the text of the variable bill amount
        //the float() converts it to a float amount
        //the exclamation mark 
        //var X:String = "" (variable x is a string); variables have to be initialized
        //var y:Int? the ? means that it is an optional variable, so we don't have to initialize it
        //var z:Int = 5
        //y=5
        //nil means nothing in swift so y is nil or an int so with the ? we make it nil
        // if we print both than when y is pring than it pops out 5 and if z is printed we get optional(5)
        //to get to print the actual value we use an exclamation mark so y! than we get 5

        
        //we get the index that is selected, either 0, 1 or 2
        if (billAmount.text! == ""){
            return
        }
       
            let userInput = Float(billAmount.text!)
        
        let index:Int = segTipPercent.selectedSegmentIndex
        //if it's 0 its 15%, etc
        var tipRate:Float = 0.15
        
        if index == 0 {
            tipRate = 0.15
        }
        else if index == 1{
            tipRate = 0.18
        }
        else if index == 2{
            tipRate = 0.2
        }
        else{
            tipRate = 0.25
        }
        //getting tip amount by multiplying user input with the tip rate we get from the segmented control
        let tip = userInput! * tipRate
        //format give the string only two decimal values
        let display = String(format: "$%.2f",tip)
        lblTipAmount.text = display
    }
    
}

