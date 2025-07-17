import { Component, Input } from '@angular/core';
import { EditTale } from '../../business/models/tale.model';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-tales-card-edit',
  imports: [RouterModule],
  templateUrl: './tales-card-edit.component.html',
  styleUrl: './tales-card-edit.component.scss'
})
export class TalesCardEditComponent {
  @Input() tale!: EditTale;

}
