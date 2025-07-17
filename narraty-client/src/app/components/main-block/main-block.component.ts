import { Component } from '@angular/core';
import { VersionBlockComponent } from "../version-block/version-block.component";

@Component({
  selector: 'app-main-block',
  imports: [VersionBlockComponent],
  templateUrl: './main-block.component.html',
  styleUrl: './main-block.component.scss'
})
export class MainBlockComponent {

}
