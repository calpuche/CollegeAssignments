//
//  ViewController.swift
//  loginApp
//
//  Created by Carlos Alpuche on 2/8/17.
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

    @IBOutlet weak var txtUserName: UITextField!

    @IBOutlet weak var txtPassword: UITextField!
    @IBAction func btnLogin(_ sender: Any) {
        self.txtUserName.resignFirstResponder()
        self.txtPassword.resignFirstResponder()
        if(txtUserName.text == "Carlos" && txtPassword.text == "Rules"){
            //old code saying you logged int
          /*  let alertController = UIAlertController(title: "Hello!",message:"You have succesfully logged in!",preferredStyle: UIAlertControllerStyle.alert)
            
            alertController.addAction(UIAlertAction(title:"Okay",style: UIAlertActionStyle.default, handler: nil))
            
            present(alertController, animated: true, completion:  nil)*/
            
            //"SecondVC is the name we gave the logged in screen
            //loggedinviewcontroller is the name of the .swift for the VC for the second screen
            let goToSecondVC = self.storyboard?.instantiateViewController(withIdentifier: "SecondVC") as! LoggedInViewController
            self.present(goToSecondVC, animated: true, completion: nil)
            
            
            
            
            
            
            
            
            
            
            
            
        }
        else{
            let alertController = UIAlertController(title: "Sorry",message:"Incorrect Information! Please try again.",preferredStyle: UIAlertControllerStyle.alert)
            
            alertController.addAction(UIAlertAction(title:"Okay",style: UIAlertActionStyle.default, handler: nil))
            
            present(alertController, animated: true, completion:  nil)
        }
    }
    //we are overriding a function called touchesBegan so that when a person touches outside the login stuff the keyboard goes away
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        //end anytype of editing
        self.view.endEditing(true)

    }
    
    
    
    
    
}

