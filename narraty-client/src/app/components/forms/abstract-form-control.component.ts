import { ControlValueAccessor } from '@angular/forms';

export abstract class AbstractFormControlComponent<T = any> implements ControlValueAccessor {
  value!: T;
  disabled = false;
  onChange = (_: T) => {};
  onTouched = () => {};

  writeValue(value: T): void {
    this.value = value;
    this.onWriteValue(value);
  }

  registerOnChange(fn: (_: T) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  protected onWriteValue(_value: T): void {}
}