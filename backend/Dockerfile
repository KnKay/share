FROM python

EXPOSE 8000

COPY . /app
WORKDIR /app

RUN pip install -r requirements.txt --no-cache-dir
ENTRYPOINT ["python3"]
CMD ["manage.py", "runserver", "0.0.0.0:8000"]
