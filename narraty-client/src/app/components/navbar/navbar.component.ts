import { Component } from '@angular/core';
import { MainBlockComponent } from '../main-block/main-block.component';
import { LinkButtonComponent } from '../link-button/link-button.component';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../business/services/auth.service';

@Component({
  selector: 'app-navbar',
  imports: [LinkButtonComponent, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
  isConnected: boolean = false;

  constructor(private authService: AuthService) {}
  
  ngOnInit() {
    this.isConnected = this.authService.isLoggedIn();
  }
  logout() {
    this.authService.logout();
    this.isConnected = false;
  }
}
