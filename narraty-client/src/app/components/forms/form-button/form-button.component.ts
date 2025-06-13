import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-form-button',
  imports: [],
  templateUrl: './form-button.component.html',
  styleUrl: './form-button.component.scss'
})
export class FormButtonComponent {
  @Input() name: string = '';
}
