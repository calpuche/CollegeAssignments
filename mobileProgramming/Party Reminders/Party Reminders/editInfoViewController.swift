//
//  editInfoViewController.swift
//  Party Reminders
//
//  Created by Carlos Alpuche on 5/4/17.
//  Copyright © 2017 Carlos Alpuche. All rights reserved.
//

import UIKit

class editInfoViewController: UIViewController {
    var theImagePassed = UIImageView()
    override func viewDidLoad() {
        super.viewDidLoad()
        imageView.image=theImagePassed.image
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

    @IBOutlet weak var txtPlace: UITextField!
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var txtPeople: UITextField!
    @IBOutlet weak var lblStatus: UILabel!
    var currentTime = Date()
    @IBAction func btnSave(_ sender: Any) {
        if((txtPlace.text?.isEmpty)!){
            lblStatus.text = "* Place is Required *"
            lblStatus.textColor = UIColor.red
            return
        }
        if((txtPeople.text?.isEmpty)!){
            lblStatus.text = "* People are Required *"
            lblStatus.textColor = UIColor.red
            return
        }
        if((imageView.image==nil)){
            lblStatus.text = "* Picture is Required *"
            lblStatus.textColor = UIColor.red
            return
        }
        let newPhoto = Photos(context: context)
        newPhoto.date = currentTime as NSDate?
        newPhoto.people = txtPeople.text
        newPhoto.place = txtPlace.text
        let data = UIImagePNGRepresentation(imageView.image!) as NSData?
        newPhoto.photo = data
        appDelegate.saveContext()
        self.performSegue(withIdentifier: "goBack", sender: self)

    }
    override func shouldPerformSegue(withIdentifier identifier: String?, sender: Any?) -> Bool {
        if let ident = identifier {
            if ident == "goBack" {
                if ((txtPlace.text?.isEmpty)! || (txtPeople.text?.isEmpty)!) || (imageView.image==nil){
                    return false
                }
            }
        }
        return true
    }
}
