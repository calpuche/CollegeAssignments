//
//  MyViewController.swift
//  thirdInClassApp
//
//  Created by Carlos Alpuche on 2/1/17.
//  Copyright © 2017 Carlos Alpuche. All rights reserved.
//

import UIKit

class MyViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

    @IBOutlet weak var lblBrightnessValue: UILabel!
    
    @IBOutlet weak var sliderBrightnessOutlet: UISlider!
    
    @IBAction func sliderBrightnessAction(_ sender: Any) {
        //dynamically change value to what ever the position is slider
        lblBrightnessValue.text = String(Int(sliderBrightnessOutlet.value))
        //change the actual brightness
        UIScreen.main.brightness = CGFloat(sliderBrightnessOutlet.value/100)
    }
   
    
    
    
    
    
    
}
