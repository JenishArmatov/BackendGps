import React from "react";
import { useForm } from "react-hook-form";
import "./LoginForm.scss";
import { useLoginMutation } from "../../redux/apiSlice";
import { useDispatch } from "react-redux";
import { setToken } from "../../redux/slices/tokenSlices";

const LoginForm = () => {
  const dispatch = useDispatch()
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();
  const [login, { isLoading, error }] = useLoginMutation();

  const onSubmit = async (data) => {
    const formattedData = {
      username: data.login, 
      password: data.password,
    };
  
    console.log("📤 Отправляем в API:", JSON.stringify(formattedData));
  
    try {
      const response = await login(formattedData).unwrap();
      console.log("✅ Успешный вход:", response);
      
      if (response.token) {
        dispatch(setToken(response.token));
        localStorage.setItem("token", response.token);
        window.location.href = "/";
      } else {
        console.error("⚠️ Токен не получен");
      }
    } catch (err) {
      console.error("❌ Ошибка входа:", err);
    }
  };
  

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit(onSubmit)}>
        <h2>Вход в аккаунт</h2>

        <label className="form-label">
          Логин *
          <input
            type="text"
            className={`form-input ${errors.login ? "input-error" : ""}`}
            {...register("login", {
              required: "Введите логин",
              minLength: { value: 3, message: "Логин должен содержать не менее 3 символов" },
              maxLength: { value: 20, message: "Логин не может превышать 20 символов" },
              pattern: { value: /^[a-zA-Z0-9_]+$/, message: "Логин может содержать только буквы, цифры и _" },
            })}
          />
          {errors.login && <span className="error-text">{errors.login.message}</span>}
        </label>

        <label className="form-label">
          Пароль *
          <input
            type="password"
            className={`form-input ${errors.password ? "input-error" : ""}`}
            {...register("password", {
              required: "Введите пароль",
              minLength: { value: 5, message: "Пароль должен содержать не менее 6 символов" },
            })}
          />
          {errors.password && <span className="error-text">{errors.password.message}</span>}
        </label>

        <button type="submit" className="submit-button" disabled={isLoading}>
          {isLoading ? "Вход..." : "Войти"}
        </button>

        {error && <p className="error-text">Ошибка входа: {error.data?.message || "Неверные данные"}</p>}
      </form>
    </div>
  );
};

export default LoginForm;
