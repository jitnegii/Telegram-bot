<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${title}</title>
  </head>
  <body style="background-color: black">
    <div
      style="
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        padding: 10px;
      "
    >
      <video controls name="media">
        <source
          src=${src}
          type="audio/mp3"
        />
      </video>
      <p
        style="
          color: white;
          font-family: Arial, Helvetica, sans-serif;
          font-weight: 400;
          text-align: center;
        "
      >
        ${msg}
      </p>
    </div>
  </body>
</html>
