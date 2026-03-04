/**
 * ARQUIVO DE EXEMPLOS E DICAS DE USO DO CATÁLOGO DE FILMES
 * 
 * Este arquivo contém exemplos de como estender e melhorar
 * o projeto Angular atual.
 */

// ============================================================================
// 1. EXEMPLO: Adicionar um Filtro de Rating
// ============================================================================

// Adicione este código em catalog.component.ts

/*
export class CatalogComponent implements OnInit {
  catalogItems: CatalogItem[] = [];
  filteredItems: CatalogItem[] = [];
  minRating: number = 0;

  // ... código existente ...

  loadCatalog(): void {
    // ... código existente ...
    this.catalogService.getCatalogByUserId(this.userId).subscribe({
      next: (data: CatalogItem[]) => {
        this.catalogItems = data;
        this.applyFilter(); // Aplicar filtro após carregar
        this.isLoading = false;
      },
      error: (error) => {
        // ... tratamento de erro ...
      }
    });
  }

  applyFilter(): void {
    this.filteredItems = this.catalogItems.filter(
      item => item.rating >= this.minRating
    );
  }

  onFilterChange(): void {
    this.applyFilter();
  }
}
*/

// E no template HTML:
/*
<div>
  <label>Filtrar por rating mínimo:
    <input 
      type="range" 
      min="0" 
      max="5" 
      [(ngModel)]="minRating"
      (change)="onFilterChange()"
    >
    {{ minRating }}/5
  </label>
</div>

<div *ngFor="let item of filteredItems" class="movie-item">
  <!-- ... conteúdo do filme ... -->
</div>
*/

// ============================================================================
// 2. EXEMPLO: Adicionar Busca por Nome de Filme
// ============================================================================

/*
export class CatalogComponent implements OnInit {
  catalogItems: CatalogItem[] = [];
  searchTerm: string = '';

  filterByName(): CatalogItem[] {
    if (!this.searchTerm) {
      return this.catalogItems;
    }
    
    return this.catalogItems.filter(item =>
      item.name.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }
}
*/

// Template:
/*
<input 
  type="text" 
  [(ngModel)]="searchTerm"
  placeholder="Buscar por nome do filme"
  class="search-input"
>

<div *ngFor="let item of filterByName()" class="movie-item">
  <!-- ... conteúdo ... -->
</div>
*/

// ============================================================================
// 3. EXEMPLO: Adicionar Paginação
// ============================================================================

/*
export class CatalogComponent implements OnInit {
  catalogItems: CatalogItem[] = [];
  itemsPerPage: number = 6;
  currentPage: number = 1;

  getPaginatedItems(): CatalogItem[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    return this.catalogItems.slice(startIndex, endIndex);
  }

  getTotalPages(): number {
    return Math.ceil(this.catalogItems.length / this.itemsPerPage);
  }

  nextPage(): void {
    if (this.currentPage < this.getTotalPages()) {
      this.currentPage++;
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  }
}
*/

// Template:
/*
<div class="pagination">
  <button 
    (click)="previousPage()"
    [disabled]="currentPage === 1"
  >
    ← Anterior
  </button>
  
  <span>Página {{ currentPage }} de {{ getTotalPages() }}</span>
  
  <button 
    (click)="nextPage()"
    [disabled]="currentPage === getTotalPages()"
  >
    Próximo →
  </button>
</div>
*/

// ============================================================================
// 4. EXEMPLO: Melhorar o Serviço com Cache
// ============================================================================

/*
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap } from 'rxjs/operators';
import { CatalogItem } from '../models/catalog-item.model';

@Injectable({
  providedIn: 'root'
})
export class CatalogService {
  private apiUrl = 'http://localhost:8080/catalog';
  private cache = new Map<string, CatalogItem[]>();

  constructor(private http: HttpClient) { }

  getCatalogByUserId(userId: string): Observable<CatalogItem[]> {
    // Verificar se está em cache
    if (this.cache.has(userId)) {
      return of(this.cache.get(userId)!);
    }

    // Se não está em cache, fazer requisição
    return this.http.get<CatalogItem[]>(`${this.apiUrl}/${userId}`)
      .pipe(
        // Guardar no cache após receber
        tap(data => this.cache.set(userId, data))
      );
  }

  // Limpar cache
  clearCache(): void {
    this.cache.clear();
  }

  // Limpar cache de um usuário específico
  clearUserCache(userId: string): void {
    this.cache.delete(userId);
  }
}
*/

// ============================================================================
// 5. EXEMPLO: Adicionar Loading com Spinners
// ============================================================================

/*
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-loading',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="spinner" *ngIf="isLoading">
      <div class="spinner-border"></div>
      <p>{{ message }}</p>
    </div>
  `,
  styles: [`
    .spinner {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 40px;
    }

    .spinner-border {
      border: 4px solid #f3f3f3;
      border-top: 4px solid #667eea;
      border-radius: 50%;
      width: 40px;
      height: 40px;
      animation: spin 1s linear infinite;
    }

    @keyframes spin {
      0% { transform: rotate(0deg); }
      100% { transform: rotate(360deg); }
    }

    p {
      margin-top: 15px;
      color: #666;
    }
  `]
})
export class LoadingComponent {
  @Input() isLoading: boolean = false;
  @Input() message: string = 'Carregando...';
}
*/

// ============================================================================
// 6. EXEMPLO: Adicionar Dark Mode
// ============================================================================

/*
export class AppComponent {
  isDarkMode: boolean = false;

  toggleDarkMode(): void {
    this.isDarkMode = !this.isDarkMode;
    
    // Aplicar classe ao documento
    if (this.isDarkMode) {
      document.body.classList.add('dark-mode');
    } else {
      document.body.classList.remove('dark-mode');
    }

    // Salvar no localStorage
    localStorage.setItem('darkMode', this.isDarkMode.toString());
  }

  ngOnInit(): void {
    // Recuperar preferência do localStorage
    const savedTheme = localStorage.getItem('darkMode');
    if (savedTheme) {
      this.isDarkMode = savedTheme === 'true';
      document.body.classList.toggle('dark-mode', this.isDarkMode);
    }
  }
}
*/

// CSS para dark mode:
/*
body.dark-mode {
  background-color: #1a1a1a;
  color: #fff;
}

body.dark-mode .card,
body.dark-mode .movie-item {
  background-color: #2a2a2a;
  color: #fff;
}

body.dark-mode h1, h2, h3 {
  color: #fff;
}
*/

// ============================================================================
// 7. EXEMPLO: Adicionar Error Handling Melhorado
// ============================================================================

/*
export class CatalogComponent implements OnInit {
  errorMessage: string = '';
  errorType: 'network' | 'notfound' | 'server' = 'server';

  loadCatalog(): void {
    this.catalogService.getCatalogByUserId(this.userId).subscribe({
      next: (data) => {
        this.catalogItems = data;
        this.isLoading = false;
        this.errorMessage = '';
      },
      error: (error) => {
        this.isLoading = false;
        
        if (error.status === 0) {
          this.errorType = 'network';
          this.errorMessage = 'Erro de conexão: Servidor não está respondendo.';
        } else if (error.status === 404) {
          this.errorType = 'notfound';
          this.errorMessage = `Usuário "${this.userId}" não encontrado.`;
        } else if (error.status >= 500) {
          this.errorType = 'server';
          this.errorMessage = 'Erro no servidor. Tente novamente mais tarde.';
        } else {
          this.errorType = 'server';
          this.errorMessage = `Erro: ${error.message}`;
        }
      }
    });
  }
}
*/

// ============================================================================
// 8. EXEMPLO: Adicionar Testes Unitários
// ============================================================================

/*
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CatalogComponent } from './catalog.component';
import { CatalogService } from '../services/catalog.service';
import { of } from 'rxjs';

describe('CatalogComponent', () => {
  let component: CatalogComponent;
  let fixture: ComponentFixture<CatalogComponent>;
  let catalogService: CatalogService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CatalogComponent],
      providers: [CatalogService]
    }).compileComponents();

    fixture = TestBed.createComponent(CatalogComponent);
    component = fixture.componentInstance;
    catalogService = TestBed.inject(CatalogService);
  });

  it('deve exibir a lista de filmes', () => {
    const mockData = [
      { name: 'Filme 1', description: 'Desc 1', rating: 4 },
      { name: 'Filme 2', description: 'Desc 2', rating: 5 }
    ];

    spyOn(catalogService, 'getCatalogByUserId').and.returnValue(of(mockData));

    component.loadCatalog();

    expect(component.catalogItems).toEqual(mockData);
  });

  it('deve exibir mensagem de erro quando falhar', () => {
    spyOn(catalogService, 'getCatalogByUserId').and.returnValue(
      throwError(new Error('Erro'))
    );

    component.loadCatalog();

    expect(component.errorMessage).toContain('Erro ao buscar catálogo');
  });
});
*/

// ============================================================================
// 9. EXEMPLO: Adicionar Animações
// ============================================================================

/*
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  selector: 'app-catalog',
  // ...
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('300ms', style({ opacity: 1 }))
      ]),
      transition(':leave', [
        animate('300ms', style({ opacity: 0 }))
      ])
    ]),
    trigger('slideDown', [
      transition(':enter', [
        style({ transform: 'translateY(-20px)', opacity: 0 }),
        animate('300ms ease-out', style({ transform: 'translateY(0)', opacity: 1 }))
      ])
    ])
  ]
})
export class CatalogComponent {
  // ...
}
*/

// Template:
/*
<div @fadeInOut>
  <div *ngFor="let item of catalogItems" @slideDown class="movie-item">
    <!-- ... -->
  </div>
</div>
*/

// ============================================================================
// 10. EXEMPLO: Adicionar Interceptor para Headers
// ============================================================================

/*
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // Adicionar token no header
    const token = localStorage.getItem('authToken');
    
    if (token) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(req);
  }
}

// Em main.ts:
// providers: [
//   provideHttpClient(withInterceptors([authInterceptor]))
// ]
*/

// ============================================================================

// Este arquivo serve como referência. Copie e adapte os exemplos conforme necessário!
