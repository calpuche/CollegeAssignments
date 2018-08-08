//
//  ViewController.swift
//  navViewControllers
//
//  Created by Carlos Alpuche on 2/20/17.
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

    @IBOutlet weak var txtboxUsername: UITextField!

    //
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        //We are creating a variable (SecondVC) that connects this swift file to the other swift file
        let SecondVC :SecondViewController = segue.destination as! SecondViewController
        
        
        SecondVC.name = txtboxUsername.text!
    }
    
    
    }

