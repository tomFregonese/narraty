import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-link-button',
  imports: [RouterLink],
  templateUrl: './link-button.component.html',
  styleUrl: './link-button.component.scss'
})
export class LinkButtonComponent {
  @Input() name: string = '';
  @Input() link: string = '';
  @Input() returnIcon: boolean = false;
  @Input() secondary: boolean = false;
  @Input() button: boolean = false;
  private routerSubscription: any;
  protected samePageUnderline: string = '';

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.samePageUnderline = this.getCurrentRoute();
    this.routerSubscription = this.router.events.subscribe(() => {
      this.samePageUnderline = this.getCurrentRoute();
    });
  }

  ngOnDestroy(): void {
    if (this.routerSubscription) {
      this.routerSubscription.unsubscribe();
    }
  }

  getCurrentRoute(): string {
    return this.router.url;
  }
}
