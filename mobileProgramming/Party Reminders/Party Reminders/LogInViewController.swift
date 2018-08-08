//
//  LogInViewController.swift
//  Party Reminders
//
//  Created by Carlos Alpuche on 5/3/17.
//  Copyright © 2017 Carlos Alpuche. All rights reserved.
//

import UIKit

class LogInViewController: UIViewController {
    let savedData = UserDefaults.standard

    func dismissKeyboard()
    {
        view.endEditing(true)
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        let tap : UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(LogInViewController.dismissKeyboard))
        view.addGestureRecognizer(tap)
        
        
        // Do any additional setup after loading the view.
    }
    override func viewDidAppear(_ animated: Bool)
    {
        if(savedData.bool(forKey: "loggedIn")){
            self.performSegue(withIdentifier: "nextView", sender: self)
        }
        super.viewDidAppear(false)
    }
    
    
    
    

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBOutlet weak var txtUser: UITextField!

    @IBOutlet weak var txtPassword: UITextField!
    @IBOutlet weak var lblStatus: UILabel!
    
    @IBAction func btnSignIn(_ sender: Any) {
        
        //make sure the username and password fields are entered
        if ((txtUser.text?.isEmpty)! || (txtPassword.text?.isEmpty)!){
            lblStatus.text="* Please Enter Information *"
            lblStatus.textColor = UIColor.red
            return
        }
        let myUrl = URL(string: "https://calpuche.create.stedwards.edu/MobileDev/signInUser.php")
        var request = URLRequest(url:myUrl!)
        request.httpMethod="POST" //come a query string
        
        let postString = "a=\(txtUser.text!)"
        request.httpBody=postString.data(using: String.Encoding.utf8)
        
        let task = URLSession.shared.dataTask(with: request){(data:Data?, response: URLResponse?, error:Error?) in
            
            if (error != nil){
                print("error=\(error)")
            }
            
            //you can print out response object
            print("response = \(response)")
            
            do {
                let myJSON = try JSONSerialization.jsonObject(with: data!, options: .mutableContainers) as? NSArray
                
                //parsing the json as pile list
                if let parsed_data = myJSON{
                    DispatchQueue.main.async() {
                        
                        //check if username does not exist
                        if parsed_data.count == 0{
                            let alertController = UIAlertController(title: "Accessed Denied", message: "Username does not exist", preferredStyle: UIAlertControllerStyle.alert)
                            alertController.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                            
                            self.present(alertController,animated:true,completion: nil)
                            
                            self.lblStatus.text = "* Unsuccessful Log In *"
                            self.lblStatus.textColor = UIColor.red
                            self.txtUser.text = ""
                            self.txtUser.text = ""
                            return
                        } // end of username does not exist
                        //if username exists, lets check for the password
                        
                        let userPass = parsed_data[0] as? NSDictionary
                        
                        //if password is correct
                        
                        if(self.txtPassword.text==userPass!["password"]! as? String){
                            self.lblStatus.text = "* Successful Log In *"
                            self.lblStatus.textColor = UIColor.green
                            let alertController = UIAlertController(title: "Welcome", message: "Successfully Logged In", preferredStyle: UIAlertControllerStyle.alert)
                            alertController.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                            self.savedData.set(true, forKey: "loggedIn")
                            self.performSegue(withIdentifier: "nextView", sender: self)
                            
                            //self.present(alertController,animated:true,completion: nil)
                            self.txtUser.text = ""
                            self.txtPassword.text = ""
                        } //end of password is correct
                        else{
                            let alertController = UIAlertController(title: "Access Denied", message: "Incorrect Password", preferredStyle: UIAlertControllerStyle.alert)
                            alertController.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                            self.present(alertController,animated:true,completion: nil)
                            self.txtPassword.text = ""
                            self.lblStatus.text = "* Unsuccessful Log In *"
                            self.lblStatus.textColor = UIColor.red
                        }//End Else
                        
                        
                    }//end Dispatch Queue
                }//End if let parsed_date
            }//end do statement
            catch{
                print(error)
            } //end of catch
        } //end Task
        task.resume()
        
    }

}
