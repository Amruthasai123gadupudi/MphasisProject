import { TestBed } from '@angular/core/testing';

import { PlatformConfigService } from './platform-config.service';

describe('PlatformConfigService', () => {
  let service: PlatformConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlatformConfigService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
