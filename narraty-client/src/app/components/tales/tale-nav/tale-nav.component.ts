import { Component } from '@angular/core';
import { LinkButtonComponent } from '../../link-button/link-button.component';
import { Input } from '@angular/core';

@Component({
  selector: 'app-tale-nav',
  imports: [LinkButtonComponent],
  templateUrl: './tale-nav.component.html',
  styleUrl: './tale-nav.component.scss',
})
export class TaleNavComponent {
  @Input() taleTitle: string = '';
}
