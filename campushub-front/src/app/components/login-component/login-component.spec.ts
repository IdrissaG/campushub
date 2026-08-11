import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from './login-component';
import { AuthService } from '../../services/auth.service';
import { provideRouter, Router } from '@angular/router';
import { of, throwError } from 'rxjs';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authServiceSpy: any;
  let router: Router;

  beforeEach(async () => {
    authServiceSpy = {
      login: vi.fn().mockReturnValue(of({ token: 'fake-jwt-token' }))
    };

    await TestBed.configureTestingModule({
      imports: [LoginComponent],
      providers: [
        provideRouter([]),
        { provide: AuthService, useValue: authServiceSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize login form with empty values and invalid state', () => {
    expect(component.loginForm.get('email')?.value).toBe('');
    expect(component.loginForm.get('motDePasse')?.value).toBe('');
    expect(component.loginForm.invalid).toBe(true);
  });

  it('should validate email format and required fields', () => {
    const emailControl = component.loginForm.get('email');
    const pwdControl = component.loginForm.get('motDePasse');

    emailControl?.setValue('invalid-email');
    expect(emailControl?.errors?.['email']).toBeTruthy();

    emailControl?.setValue('ibrahima@example.com');
    expect(emailControl?.errors).toBeNull();

    pwdControl?.setValue('password123');
    expect(pwdControl?.errors).toBeNull();
    expect(component.loginForm.valid).toBe(true);
  });

  it('should call authService and navigate to /etudiants on successful login', () => {
    const navigateSpy = vi.spyOn(router, 'navigate');

    component.loginForm.setValue({
      email: 'ibrahima@example.com',
      motDePasse: 'password123'
    });

    component.onSubmit();

    expect(authServiceSpy.login).toHaveBeenCalledWith({
      email: 'ibrahima@example.com',
      motDePasse: 'password123'
    });
    expect(navigateSpy).toHaveBeenCalledWith(['/etudiants']);
  });

  it('should display error message on invalid credentials (error response)', () => {
    authServiceSpy.login.mockReturnValue(throwError(() => new Error('Unauthorized')));

    component.loginForm.setValue({
      email: 'wrong@example.com',
      motDePasse: 'wrongpassword'
    });

    component.onSubmit();

    expect(component.erreur()).toBe('Identifiants invalides');
  });
});
