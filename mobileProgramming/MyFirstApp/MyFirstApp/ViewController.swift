//
//  ViewController.swift
//  MyFirstApp
//
//  Created by Carlos Alpuche on 1/25/17.
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

    @IBAction func btnWelcome(_ sender: Any) {
        
        let alertController = UIAlertController(title: "Welcome to my first app!",message:"Hello World!",preferredStyle: UIAlertControllerStyle.alert)
        
        alertController.addAction(UIAlertAction(title:"Okay",style: UIAlertActionStyle.default, handler: nil))
     
        present(alertController, animated: true, completion:  nil)
    }

}

