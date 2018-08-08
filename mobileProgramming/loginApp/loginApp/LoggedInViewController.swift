//
//  LoggedInViewController.swift
//  loginApp
//
//  Created by Carlos Alpuche on 2/13/17.
//  Copyright © 2017 Carlos Alpuche. All rights reserved.
//

import UIKit

class LoggedInViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    @IBAction func btnLogOut(_ sender: Any) {
        let goBackToLogin = self.storyboard?.instantiateViewController(withIdentifier: "FirstVC") as! ViewController
        self.present(goBackToLogin, animated: true, completion: nil)
        
        
        
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
