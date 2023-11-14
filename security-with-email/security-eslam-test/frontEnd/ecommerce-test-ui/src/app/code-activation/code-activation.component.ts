import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-code-activation',
  templateUrl: './code-activation.component.html',
  styleUrls: ['./code-activation.component.css']
})
export class CodeActivationComponent {

  codeForm!: FormGroup;
  hidePassword:boolean = true;

  email:string = '';


  constructor(private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router){}


  ngOnInit(): void{
    this.codeForm = this.fb.group({
        code:[null, [Validators.required]],
        })

    this.email = sessionStorage.getItem("emailAtive")
  }


  onSubmit(){

  }


}
