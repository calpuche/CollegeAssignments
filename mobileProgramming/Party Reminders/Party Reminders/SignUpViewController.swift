//
//  SignUpViewController.swift
//  Party Reminders
//
//  Created by Carlos Alpuche on 5/3/17.
//  Copyright © 2017 Carlos Alpuche. All rights reserved.
//

import UIKit

class SignUpViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        let tap : UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(LogInViewController.dismissKeyboard))
        view.addGestureRecognizer(tap)

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    func dismissKeyboard()
    {
        view.endEditing(true)
    }

    
    @IBOutlet weak var txtFirstName: UITextField!

    @IBOutlet weak var txtLastName: UITextField!
    
    @IBOutlet weak var txtEmail: UITextField!

    @IBOutlet weak var txtUsername: UITextField!
    
    @IBOutlet weak var txtPassword: UITextField!
    @IBOutlet weak var lblStatus: UILabel!
    
    @IBAction func btnSignUp(_ sender: Any) {
        //make sure the username and password fields are entered
        if ((txtFirstName.text?.isEmpty)! || (txtLastName.text?.isEmpty)! || (txtEmail.text?.isEmpty)! || (txtUsername.text?.isEmpty)! || (txtPassword.text?.isEmpty)!){
            lblStatus.text="* Please Enter Information *"
            lblStatus.textColor = UIColor.red
            return
        }
        //put thie link of the php file here. the php file connects the mysql and swift
        let request = NSMutableURLRequest(url:NSURL(string:"https://calpuche.create.stedwards.edu/MobileDev/insertUser.php")! as URL)
        
        request.httpMethod = "POST"
        
        let postString = "a=\(txtFirstName.text!)&b=\(txtLastName.text!)&c=\(txtEmail.text!)&d=\(txtUsername.text!)&e=\(txtPassword.text!)"
        request.httpBody = postString.data(using: String.Encoding.utf8)
        
        let task = URLSession.shared.dataTask(with: request as URLRequest){
            data,response,error in
            
            if error != nil {
                print("error=\(error)")
                return
            }
            print("response =  \(response)")
            
            let responseString = NSString(data: data!,encoding: String.Encoding.utf8.rawValue)
            print("responseString= \(responseString)")
            
            DispatchQueue.main.async {
                //check if username already exists, mysql will throw an error in case of duplicate keys
                if(responseString!=="Success"){
                    self.txtFirstName.text=""
                    self.txtLastName.text=""
                    self.txtEmail.text=""
                    self.txtUsername.text=""
                    self.txtPassword.text=""
                    self.performSegue(withIdentifier: "signIn", sender: self)
                }//end if for success
                else{
                    let alertController = UIAlertController(title: "Cannot Create New Account", message: "Username Already Exists", preferredStyle: UIAlertControllerStyle.alert)
                    alertController.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                    self.present(alertController,animated:true,completion:nil)
                }//End Else
                self.txtFirstName.text=""
                self.txtLastName.text=""
                self.txtEmail.text=""
                self.txtUsername.text=""
                self.txtPassword.text=""
            }//end of Dispatch queue synce
            
        }//end of task
        task.resume()
        
        
        
    }
   
    
}
