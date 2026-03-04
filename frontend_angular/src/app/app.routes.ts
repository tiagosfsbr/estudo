import { Routes } from '@angular/router';
import { CatalogComponent } from './catalog/catalog.component';

export const routes: Routes = [
  { path: '', component: CatalogComponent },
  { path: 'catalog', component: CatalogComponent },
  { path: '**', redirectTo: '' }
];
